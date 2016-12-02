# Because of 'suspend=y', javaws waits for the Remote-Run-Configuration to start.
set JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005
javaws http://localhost:8123/files/GameOfLife.jnlp