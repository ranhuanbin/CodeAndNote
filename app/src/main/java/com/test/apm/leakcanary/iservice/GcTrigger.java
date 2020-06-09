/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test.apm.leakcanary.iservice;


/**
 * Called when a watched reference is expected to be weakly reachable, but hasn't been enqueued
 * in the reference queue yet. This gives the application a hook to run the GC before the {@link
 * RefWatcher} checks the reference queue again, to avoid taking a heap dump if possible.
 */
public interface GcTrigger {

    void runGc();
}