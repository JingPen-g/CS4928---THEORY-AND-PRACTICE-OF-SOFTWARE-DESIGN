- Which smells did you remove, and where?

God Class and Long Method -> OrderManagerGod handled product creation, pricing, discounting, tax, payment, and printing.

Primitive Obsession -> Replaced magic numbers with injected policies like FixedRateTaxPolicy(10)

Feature Envy / Shotgun Surgery -> Moved pricing and discount logic out into PricingService, so changes in tax or discount now occur in one place.

Global / Static State -> Removed static fields (TAX_PERCENT, LAST_DISCOUNT_CODE) and injected dependencies instead for safer, testable design.


- Which refactorings did you apply (by name), and why?

Extract Class -> Split the responsibilities of OrderManagerGod into smaller collaborators

Dependency Injection -> Made testing and substitution of dependencies easier

Replace Conditional with Polymorphism -> Avoid long if/else

Remove Static State -> Eliminated hidden coupling and made behavior instance-specific

Move Method -> Improved cohesion and reduced feature envy

- How does your new design satisfy specific SOLID principles?

Single Responsibility Principle -> Each class now has one reason to change — CheckoutService coordinates, PricingService computes, PaymentStrategy handles payment.

Open/Closed Principle -> New discount or tax types can be added by subclassing DiscountPolicy or TaxPolicy, without modifying existing code.

Liskov Substitution Principle -> All DiscountPolicy and TaxPolicy subclasses are interchangeable.

Interface Segregation Principle -> Clients depend only on small, relevant interfaces (PaymentStrategy, OrderObserver).

Dependency Inversion Principle -> CheckoutService depends on abstractions (DiscountPolicy, TaxPolicy, PaymentStrategy), not concrete implementations.

- What would be required to add a new discount type without editing existing classes?

Simply create a new class that implements the DiscountPolicy interface, and then inject it into CheckoutService.

- Why is adapting the legacy printer better than changing your domain or vendor class?

Because we have two systems, one is our domain system (use String-based printing), another one is vendor system (use byte[]).

Changing domain class would pollute clean, modern code with old technical details. Changing vendor class would break other clients who's using it, and we don't own vendor's code.

So we introduce adapter to transfer between the two.