#!/bin/sh

# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.

# -----------------------------------------------------------------------------
# Apache Maven YourKit Profiler Startup Script
#
# Environment Variable Prerequisites
#
#   JAVA_HOME       Must point at your Java Development Kit installation.
#   MAVEN_ARGS      (Optional) Arguments passed to Maven before CLI arguments.
#   MAVEN_OPTS      (Optional) Java runtime options used when Maven is executed.
#   MAVEN_SKIP_RC   (Optional) Flag to disable loading of mavenrc files.
# -----------------------------------------------------------------------------

if [ ! -f "$YJPLIB" ]; then
  echo "Error: Unable to autodetect the YJP library location. Please set YJPLIB variable" >&2
  exit 1
fi

env MAVEN_OPTS="-agentpath:$YJPLIB=onexit=snapshot,onexit=memory,tracing,onlylocal $MAVEN_OPTS" "`dirname "$0"`/mvn" "$@"
