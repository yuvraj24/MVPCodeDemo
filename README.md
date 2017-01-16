# MVPCodeDemo
The MVP pattern allows to separate the presentation layer from the business logic, so that everything about how the interface works is separated from how we represent it on screen.
In Android we usually face issues arising from the fact that Android activities are closely coupled to both interface and data access mechanisms.
For an application to be easily extensible and maintainable we need to define well separated layers. What do we do tomorrow if, instead of retrieving the same data from a database, we need to do it from a web service? We would have to redo our entire view.

MVP makes views independent from our data source. We divide the application into at least three different layers, which let us test them independently. With MVP we are able to take most of logic out from the activities so that we can test it without using instrumentation tests.

#Presenter

The presenter is responsible to act as the mediator between view and model. It retrieves data from the model and returns it be formatted to the view. But unlike the typical MVC, it also decides what happens when you interact with the view.

#View

The view, usually implemented by an Activity (it may be a Fragment, a View… depending on how the app is structured), will contain a reference to the presenter. Presenter will be ideally provided by a dependency injector such as Dagger, but in case you don’t use something like this, it will be responsible for creating the presenter object. The only thing that the view will do is calling a method from the presenter every time there is an interface action (a button click for example).

#Model

In an application with a good layered architecture, this model would only be the gateway to the domain layer or business logic. If we were using the Uncle Bob clean architecture , the model would probably be an interactor that implements a use case. But this is another topic that I’d like to discuss in future articles. For now, it is enough to see it as the provider of the data we want to display in the view.
