# Instruction
This is an plugin of ribbon. It provides a complete rule for choosing target-servers 
with the discovery metadata which registered on the consul servers.

# How to integrate
Add the [`EnableMetadataRoute`](src/main/java/com/shf/spring/cloud/ribbon/configuration/EnableMetadataRoute.java) annotation to enable ribbon route rule with discovery metadata,
[`DefaultRibbonMetadataRouteConfiguration`](src/main/java/com/shf/spring/cloud/ribbon/configuration/DefaultRibbonMetadataRouteConfiguration.java) is default configuration for registered with consul.

# How to extend
Just implement the `MetadataAwareRule` class and `MetadataAwarePredicate` class.