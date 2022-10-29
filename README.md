# Bobby Bug-Exterminator

## What does it do and how does it work

This is a resource to run integration tests on a minecraft server.
It is not meant as a library for now and hardcodes a variety of test.
The Test-Framework within this resource is a custom implementation to
test simple conditions as like as asynchronous tests which will complete later.
This concept requires tests to close themselves. 


A test method needs to be in a class implementing the TestSet interface.
It can then simply look like a junit test
```Java
@Test
public void testAEqualsB() {
	Assertions.assertEquals(a, b);
}
```

Or handle asynchronous situations like
```Java
@Test(timeout = 1000)
public void testAEqualsB(TestContext context) {
	MinecraftAssertions.assertEventCall(MyEvent.class, context);
}
```