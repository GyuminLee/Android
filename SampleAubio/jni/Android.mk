# Makefile
#	Copyright (C) Max Kastanas 2010
#	Copyright (C) Naval Saini 2010

# * This program is free software; you can redistribute it and/or modify
# * it under the terms of the GNU General Public License as published by
# * the Free Software Foundation; either version 2 of the License, or
# * (at your option) any later version.
# *
# * This program is distributed in the hope that it will be useful,
# * but WITHOUT ANY WARRANTY; without even the implied warranty of
# * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# * GNU General Public License for more details.
# *
# * You should have received a copy of the GNU General Public License
# * along with this program; if not, write to the Free Software
# * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

LOCAL_PATH:= $(call my-dir)

## libaubio module
include $(CLEAR_VARS)

LOCAL_MODULE    := libaubio
LOCAL_CFLAGS    := -Werror -g\
	-I$(LOCAL_PATH)/aubio \
	-I$(LOCAL_PATH)/aubio/io \
	-I$(LOCAL_PATH)/aubio/onset \
	-I$(LOCAL_PATH)/aubio/pitch \
	-I$(LOCAL_PATH)/aubio/spectral \
	-I$(LOCAL_PATH)/aubio/synth \
	-I$(LOCAL_PATH)/aubio/tempo \
	-I$(LOCAL_PATH)/aubio/temporal \
	-I$(LOCAL_PATH)/aubio/utils \
	-I$(LOCAL_PATH)/tempo \
	-I$(LOCAL_PATH)/include

LOCAL_SRC_FILES := $(shell cd $(LOCAL_PATH); find ./aubio/ -type f -name '*.c'; find ./aubio/ -type f -name '*.cpp'; find ./tempo/ -type f -name '*.c')
LOCAL_LDLIBS    := \
                   -llog -lm

include $(BUILD_SHARED_LIBRARY)
