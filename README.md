#ECS - A Java implementation of the Entity-Component-System pattern.

While there are some modifications, this ECS library is loosely based on the ECS pattern implementation described in the following post: [The Entity-Component-System](https://www.gamasutra.com/blogs/TobiasStein/20171122/310172/The_EntityComponentSystem__An_awesome_gamedesign_pattern_in_C_Part_1.php)

While there are some smaller differences, the primary differentiation is that this implementation foregoes the Memory Manager class entirely, and depends instead on Java's garbage collection for memory management.

