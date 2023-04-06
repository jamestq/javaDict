## Java Dictionary Project with Client and Server Files

Refer to the individual project folders' README for the build steps

.jar files were built using VSCode's Extension Pack for Java.

To replicate the same result
1. Install VSCode with the extension pack.
2. Open the folder of each program, i.e. dictclient or dictserver by File -> Open Folder.
3. `Ctrl+Shift+P` and select `Java: Export Jar`

## Test Steps

1. Client Connection test

* input invalid localhost and port. Expected Result: Connection refused message
* input valid host but invalid port (Number Format). Expected Result: invalid alert message 
* input valid host but invalid port. Expected Result: connection reset message --> connection could not be established because this port is not exposed.
* input valid host and valid port. Expected Result: connection established
* socket closed by server due to idling. Expected Result: alert message when trying
to carry out a query.
* pressing connect again while connected. Expected Result: disconnect prompt
* idle the client for 30 seconds. Expected Result: server should show a message indicating that the server has stopped. Any query action by the client should throw an error on the status field above the disconnect button.

2. Client Query test
* search for a word --> return meaning if found, return not found if not found
* add a new word --> if word is missing meanings, throw an error. If word already exists, throw an error. Otherwise, add.
* update a word's meaning --> if word is missing meanings, throw an error, if word already does not exist, throw an error. Otherwise, update.
* delete a word --> if word does not exist throw an error, otherwise delete.
