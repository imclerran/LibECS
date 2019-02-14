# LibECS - A Java implementation of the Entity-Component-System pattern.

While there are some modifications, this ECS library is loosely based on the ECS pattern implementation described in the following post: [The Entity-Component-System](https://www.gamasutra.com/blogs/TobiasStein/20171122/310172/The_EntityComponentSystem__An_awesome_gamedesign_pattern_in_C_Part_1.php)

While there are actually quite a number of modifications to the actual implementation, the biggest difference is the absense of a memory manager class, instead depending on Java's garbage collection for memory management.

## Using libECS

libECS is an Entity-Component-System pattern implementation to manage game objects and their properties and behaviors. The ECS pattern prevents inflexible and complex inheritance heirachies, which can sometimes lead to liskov substitution violations, where subclasses do not implement all of their parent classes behavior.

A total of 4 Manager classes are provided by this library: 3 of these manage, the nominal `Entity`, `Component`, and `System` objects. The 4th manager class is the the `EventManager`, which facilitates communication between `Systems`.

An optional ECSEngine class is also provided which links the 4 managers into one provider class.

### Entities

Entities are the base representation of an in game object. A concrete entity should implement the `IEntity` interface. Entities should contain only basic information about the object - the `IEntity` interface provides fields for id, and type. These should be the only information stored in the entity. The `id` is obtained from the EntityManager, using the `acquireEntityId()` method. The `type` is simply a string which describes the type of entity represented, such as "EnemyShipA", or "Bullet".

### Components

Components are the primary means of storing information about an entity. Components should implement the `IComponent` interface. This interface also provides id and type fields, but in this case the `id` is the id of the entity the component stores information about. The `type` is again a string that gives the type of component.

These components can then be extended to store additional information about the entity they represent. For example, a `PlayerEntity` might be composed of a `SpriteComponent`, `HitboxComponent`, `PositionComponent`, `VelocityComponent` and an `InputComponent`, which each store their associated data about the entity.

### Systems

Systems handle the logic for updating the Components belonging to an entity. As such, the `ISystem` interface, like component, stores the `id` of the related entity, as well as the `type` of system. Implementing the `ISystem` interface also requires overriding the `update()` method, which performs the logic for updating the entities components.

For example a `InputSystem` might take data from the keyboard and store it in an `InputComponent`, afterwhich an `InputInterpreterSystem` would convert input data into into Dy/Dx info in the `VelocityComponent`, as well as updating the `FireControlComponent` with information on whether the player wishes to shoot. Next the `MovementSystem` could use data from the `VelocityComponent` to update the `PositionComponent`, while the `FireControlSystem` handles shooting.

### Events and EventListeners

Events and EventListeners are used to allow interaction between different systems. These implement their respective IEvent and IEventListener interfaces, and again each specify an id and type. The `Event` specifies the `type` of event, and the `id` of the triggering entity. The `EventListener` specifies the `type` of event to listen for, and the entity `id` the listener is interested in.

Additionally, `IEvent` contains an `args` field, which is a HashMap of type `<String, String>`, to allow passing data to the listener. The `IEventListener` contains a `eventHanlder` field, which contains a callback requiring a `HashMap<String,String>` to process the the arguments provided by the event. The listener must also implement `canHandle()` which returns true if passed an id and event type matching the listener's id and type, and `handleEvent`, which calls the `eventHanlder` callback.

## Better Modularity

As you may have noticed while reading the examples above, by composing `Entities` of different `Components` and `Systems`, we can allow a high degree of modularity between entities, where only the required components and systems compose a given entity.

For example while the `Systems` and `Components` described in the __Systems__ example were applied to a player controlled `Entity`, most of those systems and components could be recycled for a scripted enemy entity, but instead of an `InputInterpreterSystem` the enemy might have a `ScriptInterpreterSystem`.

Thus we can avoid complex and rigid inheritance heirarchies, and instead use composition to define our game entities. This allows for much better flexibility, and swapping behaviors out becomes a breeze. Just replace the appropriate components and systems as needed.