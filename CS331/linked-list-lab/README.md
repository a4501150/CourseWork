# linked-list-lab

The project uses [Midje](https://github.com/marick/Midje/).

## How to run the tests

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.

## Your work

Do a "code review" of this code, with a partner, if possible.  Go line by
line and try to figure out what everything does.  Once you are done, try
writing the `delete` and `delete-all` functions, along with their tests.
