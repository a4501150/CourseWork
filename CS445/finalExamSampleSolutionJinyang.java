
/* 1(a) */
void notify (boolean state) {
        // Implementation needed

	lightson = state;

	if ((this.Coupling[1] instanceof EndOfTrain) == false) {

		this.Coupling[1].notify(state);

	}

}

// 1(b) 1(c)
class Locomotive extends Car { 

	void pushBreak() {
        // Implementation needed

		this.notify(true);

    }
    void releaseBreak() {
        // Implementation needed

        this.notify(false);

    }
} 

//2
it violated multiple oo princinples.
violates the Single Responsibility and Dependency Inversion and Liskov substitution principles. 

Let's say we have a method that fetches data from persistence, and that persistence is a set of files. Since we are dealing with files, we can have a FileNotFoundException:
public String getData(int id) throws FileNotFoundException
Now, we have a change in requirements, and our data comes from a database. Instead of a FileNotFoundException (since we are not dealing with files), we now throw an SQLException:

public String getData(int id) throws SQLException

We would now have to go through all code that uses our method and change the exception we have to check for, 
else the code won't compile. If our method gets called far and wide, that can be a lot to change/have others change. 
	It takes a lot of time, and people aren't going to be happy.

Dependency inversion says that we really shouldn't throw either of these exceptions because they expose internal implementation details we are working to encapsulate. 
Calling code needs to know what type of persistence we are using, when it really should just be worried about if the record can be retrieved. 
Instead we should throw an exception that conveys the error at the same level of abstraction as we are exposing through our API
It's should be keep all encapsulated.

As for Single Responsibility, we need to think about code that throws multiple, unrelated exceptions. Let's say we have the following method:

public Record parseFile(String filename) throws IOException, ParseException

What can we say about this method? We can tell just from the signature that it opens a file and parses it. 
When we see a conjunction, like "and" or "or" in the description of a method, we know that it is doing more than one thing; it has more than one responsibility. 
Methods with more than one responsibility are hard to manage as they can change if any of the responsibilities change. 
Instead, we should break methods up so they have a single responsibility:

public String readFile(String filename) throws IOException
public Record parse(String data) throws ParseException

We've extracted out the responsibility of reading the file from the responsibility of parsing the data. 
One side effect of this is that we can now pass in any String data to the parse data from any source: in-memory, file, network, etc. 
We can also test parse more easily now because we don't need a file on disk to run tests against.

//4a

the mehod in Server class , handleMessage method, this violates OPEN/CLOSE principle due to this is hard to extendable without modifications... 

public void handleMessage(Message m) {
        assert(m.type < 4);
        switch(m.type) {
            case LOGIN: // do stuff
            case REGISTER: // do other stuff
            case DISCONNECT: // do even more stuff
} }

this part of code cannot be extandable since if further requirements are changed, then the coder needs to understand logic inside handleMessage and modifiy the Server class as well as 
unit testing cases


THen in implementation of Server:

modify handleMessage method to this :

public void handleMessage(Message m) {
        
        m.handle();

        m = MessageTypeManager.GetByName(game); 

}

add handle to Message class :

public abstract Message {
 	
 	public int type;
	public String payload;

	public void handle() {} 
	
}

public class loginMessage extends Message {
	public void handle() {
		//do Specify stuffs
	} 
}
    
Then if we need add more messages, just build a new specific message class that extends Message without modified any client codes


4)b

MoreMessage contain two more static final fields, in other words, the derived class MoreMessage has stronger pre-conditions, which against LSP
So I would make an abstract class called  

public abstract Message {
 	
 	public int type;
	public String payload;

	public void handle() {} 
	
}

public class loginMessage extends Message {
	public void handle() {
		//do Specify stuffs
	} 
}

4)c 

This class doesnot meet SRP since it designed to satisfy three requirments :  Message Managing / sending and Connection Managing. So it has three reason to change:
connection method... 
message sent methods...
message handeling methods...

I would seperat three responsibilities to (for example) Server and MessageManager and Messanger .. Then each of these three classes has only one reason to change.

4)d

No, it does not meet ISP.. Since it does many things to satisfy client to send message, to make connection, to handle messages...

if we have to change an interface we affect even those clients that do not use the features we change..

As mentioned above, I plan to divide it to three interface, IConnectionManager(handle connections), IMessageManager(handle message types), IMessanger (sent/recive messages)

4)e

I would not put them together. According to Package Cohesion Principles, Classes that tend to be reused together belong in the same package together. Classes in a package 
should be closed together against the same kinds of changes. A change that effects a package affects all the classes in that package. 

So at least
IServer, Server would be put into one package
Message and MoreMessage would be put into another package

5)a

You should not make main class to do a lot of things, According to Single responsibility principle,
A class should have one and only one reason to change, meaning that a class should have only one job.

5)b

we always abstract from reality in a target oriented manner. like warehouse, the class name helps to understand code and reuse code,
a meaningful name is REQUIRED for oop design. So you should not make warehouse to ware, you'd rather make it more understandable like 
"cloth warehouse" for example.

5)c

You shold not reuse code by coping code. You should reuse them by extend original code, in your case you should make original code reuseble by create REQUIRED
interfaces and abstract classes and template classes. Then reuse them by inheritance.

5)d

You are against ISP and LCP in oo design. A client should never be forced to implement an interface that it doesn’t use or clients shouldn’t be forced to depend on methods they do not use.
Also, it makes the class you inherited to not be substitable. You should abstract a new abstract class to satisfy the SHARED functions of both old class and new class. Then inherite
this new abstract class to let you access useful information.

5)E

you are against Open close principle
When a new payment method comes, you need to modify the source code of payment class. This is not good, you can simply make a payment method factory class to manage payment methods. Then when you 
need modify payment methods, you can just modify a config file or just that method class.


7
this is not a good idea since it against LSP and OCP. 

you should either abstract a new interface or abstract class like ICAR to put drive() in abstract class OR
replace 

throw new UnsupportedOperationException(); 

to 
   replaceBattery();

   




