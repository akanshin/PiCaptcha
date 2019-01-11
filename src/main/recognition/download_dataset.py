from config import *

import os
from quickdraw import QuickDrawDataGroup

with open(os.path.join(DATASET_ROOT, 'categories.txt')) as f:
    for name in f.readlines():
        name = name.strip()
        data = QuickDrawDataGroup(name, max_drawings=SAMPLES_IN_CLASS, recognized=True)

        i = 0
        for sample in data.drawings:
            if i < train_samples_num:
                if not os.path.isdir(os.path.join(DATASET_ROOT, 'train/'+name)):
                    os.mkdir(os.path.join(DATASET_ROOT, 'train/'+name))
                sample.image.save(DATASET_ROOT+'/train/'+name+'/'+str(sample.key_id)+'.png')
                i += 1
            else:
                if not os.path.isdir(os.path.join(DATASET_ROOT, 'test/'+name)):
                        os.mkdir(os.path.join(DATASET_ROOT, 'test/'+name))
                sample.image.save(DATASET_ROOT+'/test/'+name+'/'+str(sample.key_id)+'.png')

