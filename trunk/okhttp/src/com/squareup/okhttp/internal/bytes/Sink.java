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
 * An alternative to OutputStream.
 */
public interface Sink {
  /** Removes {@code byteCount} bytes from {@code source} and appends them to this. */
  void write(OkBuffer source, long byteCount, Deadline deadline) throws IOException;

  /** Pushes all buffered bytes to their final destination. */
  void flush(Deadline deadline) throws IOException;

  /**
   * Pushes all buffered bytes to their final destination and releases the
   * resources held by this sink. It is an error to write a closed sink. It is
   * safe to close a sink more than once.
   */
  void close(Deadline deadline) throws IOException;
}
