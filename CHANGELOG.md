# Change Log
All notable changes to this project will be documented in this file.

The format is based on Keep a Changelog and this project adheres to Semantic Versioning.

# Version 1.0.1

# Version 1.0.0  
* first functional version of our software

### Added 
* Login and sign up components
* Hompage
* Recipe Component
* CreateRecipe Component
* model tests
* integration test
* set up build pipeline
* fridgeView

### Changed
* Changing notations on FridgeItem model, service and controller


### Fixed
* controller tests
* Recipe images
* authentification problems
* mongoDB inconsistency
* Login and routing problems


# Version 0.1.0

* Initial release with backend implementations
* Non functional release for MVP presentation - project planning, requirements analysis and UI design

### Added
* Setup DB connection
* Request mappings
* model classes: Recipe, FridgeItem and Account
* controller classes: RecipeController, FridgeItemController and AccountController
* enum classes: SecurityQuestion, Category
* IdGenerationService, FridgeItemService
* read from external API and convert to Recipe Format
* image upload feature
* Auto delete of personal recipes and fridge items after account delete
* Controller class tests

### Changed
* Changing class name from ItemValue to FridgeItem
* Making structural changes according to Maven and MongoDB new config

### Fixed
* minor fixes in commponent structure
* fixing conflits with application.properties
