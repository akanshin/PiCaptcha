import cv2
import numpy as np
import pickle
import keras
from config import *
import json

# print(keras.__version__)
import tensorflow as tf
from keras_applications.imagenet_utils import preprocess_input
from keras_applications.imagenet_utils import _preprocess_symbolic_input
from aiohttp import web


class PiCaptchaModel:
    CUSTOM_OBJECTS = {
        'tf': tf,
        'relu6': tf.nn.relu6,
        'preprocess_input': preprocess_input,
        '_preprocess_symbolic_input': _preprocess_symbolic_input
    }

    def __init__(self, model_path='./MobileNetV2.hdf5', input_shape=(224, 224)):
        self._w = input_shape[1]
        self._h = input_shape[0]

        self._model = keras.models.load_model(model_path, custom_objects=self.CUSTOM_OBJECTS)

        self._graph = tf.get_default_graph()
        with open(CATEGORY_MAPPER_PATH, 'rb') as f:
            self.category_mapper = pickle.load(f)

    def _prepare_input_data(self, image):
        img = cv2.resize(image, (self._h, self._w))
        img = img[:, :, ::-1]
        return np.expand_dims(img, axis=0)

    def run(self, image):
        model_input = self._prepare_input_data(image)

        with self._graph.as_default():
            model_output = self._model.predict(model_input)

            category_num = np.argmax(model_output)
            category_name = self.category_mapper[category_num]

        return category_name

    def get_all_class_names(self):
        return sorted(list(self.category_mapper.values()))


model = PiCaptchaModel()


async def get_picture_class(request):
    nparr = np.fromstring(await request.read(), np.uint8)
    img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    category_name = model.run(img)
    response = {'category': category_name}
    return web.Response(text=json.dumps(response))


async def get_all_classes(request):
    response = model.get_all_class_names()
    return web.Response(text=json.dumps({"value": response}))

app = web.Application()
app.router.add_route('POST', '/api/image_classifier', get_picture_class)
app.router.add_get('/api/classes', get_all_classes)

web.run_app(app, host='127.0.0.1', port=5000)
