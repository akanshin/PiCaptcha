from config import *
import os
import keras
from keras.applications.mobilenet import MobileNet
import tensorflow as tf
from keras.layers import *
from keras.models import *
from keras.losses import categorical_crossentropy
from keras.optimizers import Adam
from keras.metrics import categorical_accuracy
from keras.applications.imagenet_utils import preprocess_input

input_tensor = Input(shape=INPUT_SHAPE)
model = input_tensor
model = Lambda(preprocess_input, name='preprocessing')(model)
model = MobileNet(input_shape=(224, 224, 3), include_top=False, weights='imagenet')(model)
model = GlobalAveragePooling2D()(model)
model = Dropout(0.5)(model)
model = Dense(NUM_CLASSES, activation='softmax')(model)
model = Model(inputs=input_tensor, outputs=model)
opt = Adam(lr=LEARNING_RATE, decay=LEARNING_RATE / NUM_EPOCHS)
model.compile(loss=categorical_crossentropy, optimizer=opt, metrics=[categorical_accuracy])

import cv2

TRAIN_PATH = os.path.join(DATASET_ROOT, 'train')
filepaths = []
y = dict()
number2category = dict()
for dir_number, directory in enumerate(sorted(os.listdir(TRAIN_PATH))):
    number2category[dir_number] = directory
    for filename in os.listdir(os.path.join(TRAIN_PATH, directory)):
        filepath = os.path.join(TRAIN_PATH, directory, filename)
        filepaths.append(filepath)
        y[filepath] = dir_number


class InputGenerator(object):
    def __init__(self, filepaths, y, batch_size=8, img_size=224, shuffle=True):
        self.num_classes = NUM_CLASSES

        def generator():
            while True:
                order = np.arange(len(filepaths))
                if shuffle:
                    np.random.shuffle(order)

                n_batches = len(order) // batch_size
                for i in range(n_batches):
                    batch_order = order[i * batch_size: (i + 1) * batch_size]

                    batch_filepaths = []
                    for ord in batch_order:
                        batch_filepaths.append(filepaths[ord])

                    batch_x = np.zeros((batch_size, img_size, img_size, 3), dtype=np.uint8)
                    batch_y = np.zeros((batch_size, self.num_classes), dtype=float)
                    for k, filepath in enumerate(batch_filepaths):
                        img = cv2.imread(filepath)
                        img = cv2.resize(img, (img_size, img_size))
                        batch_x[k] = img[:, :, ::-1]
                        batch_y[k][y[filepath]] = 1

                    batch_x = batch_x.astype("float")
                    yield batch_x, batch_y

        self.generator = generator()
        self.steps = len(filepaths) // batch_size


checkpoint = keras.callbacks.ModelCheckpoint(
    os.path.join(MODEL_SAVE_PATH, MODEL_DESCR) + "_{epoch:02d}.hdf5",
    monitor='val_loss',
    verbose=0,
    save_best_only=False,
    save_weights_only=False,
    mode='auto',
    period=1
)

# print(np.unique(list(y.values())))
# print(filepaths)

model.fit_generator(InputGenerator(filepaths, y, batch_size=BATCH_SIZE).generator,
                    epochs=NUM_EPOCHS,
                    steps_per_epoch=min(1000, len(filepaths) // BATCH_SIZE),
                    callbacks=[checkpoint])
