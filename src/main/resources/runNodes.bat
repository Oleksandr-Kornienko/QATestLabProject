java -jar selenium-server-standalone-3.0.1.jar -role node -hub http://localhost:4444/grid/register -browser "browserName=firefox,maxInstances=1" -browser "browserName=chrome,maxInstances=2"