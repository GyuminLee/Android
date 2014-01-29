/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.okhttp.internal.bytes;

import java.io.IOException;

/**
 * An alternative to InputStream.
 */
public interface Source {
  /**
   * Removes {@code byteCount} bytes from this and appends them to {@code sink}.
   * Returns the number of bytes actually written.
   */
  long read(OkBuffer sink, long byteCount, Deadline deadline) throws IOException;

  /**
   * Returns the index of {@code b} in this, or -1 if this source is exhausted
   * first. This may cause this source to buffer a large number of bytes.
   */
  long indexOf(byte b, Deadline deadline) throws IOException;

  /**
   * Closes this source and releases the resources held by this source. It is an
   * error to read a closed source. It is safe to close a source more than once.
   */
  void close(Deadline deadline) throws IOException;
}
