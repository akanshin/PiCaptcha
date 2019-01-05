SAMPLES_IN_CLASS = 50
NUM_CLASSES = 345
TEST_PART = 0.15
test_samples_num = int(SAMPLES_IN_CLASS * TEST_PART)
train_samples_num = SAMPLES_IN_CLASS - test_samples_num

INPUT_SHAPE = (224, 224, 3)
LEARNING_RATE = 0.001
NUM_EPOCHS = 100
BATCH_SIZE = 25

MODEL_SAVE_PATH = './PiCapcha/models/MNv2/'
MODEL_DESCR = 'first_try'

DATASET_ROOT = './PiCapcha/dataset'
CATEGORY_MAPPER_PATH = './category_num_to_name.pickle'
