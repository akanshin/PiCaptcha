#!/usr/bin/env bash

mkdir ./PiCapcha
mkdir ./PiCapcha/dataset
mkdir ./PiCapcha/dataset/train
mkdir ./PiCapcha/dataset/test

mkdir ./PiCapcha/models

git clone https://github.com/googlecreativelab/quickdraw-dataset
cp quickdraw-dataset/categories.txt ./PiCapcha/dataset/categories.txt

./venv/bin/pip install quickdraw

python download_dataset.py