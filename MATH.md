# Location Tracking

## Uses

This algorithm is used in `LocationSubsystem`.

## Final Formula

$`x = \sum \Big [ \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) versin ( \Delta \theta ) \sin ( \theta ) + \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) \sin ( \Delta \theta ) \cos ( \theta ) \Big ] + C_x`$

$`y = \sum \Big [ \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) versin ( \Delta \theta ) \cos ( \theta ) + \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) \sin ( \Delta \theta ) \sin ( \theta ) \Big ] + C_y`$

## Derivation

Definition of a radian:
$`s = r \theta`$

Let $`s_1`$ be the encoder on the inside of the turn, $`s_2`$ be the encoder on the outside of the turn, $`r`$ be the radius of the turn, and $`w`$ be the width of the robot (between the wheels).

Then, the definition of a radian can be rewritten as:

$`ds_1 = r d \theta`$

$`ds_2 = (r + w) d \theta`$

Both of those equations can then be solved for $`r`$, giving:

$`r_1 = {ds_1 \over d \theta}`$

$`r_2 = {ds_2 \over d \theta} - w`$

And they can be averaged to get:

$`\bar r = { { \Delta s_1 + \Delta s_2 } \over 2 }`$

Let $`\bar x`$ be the center of mass along the x-axis, then the radius of the robot's arc would be:

$`r + w \bar x`$

Using a unit circle, it can be them seen that:

$`dx = ( \bar r + w \bar x ) versin ( d \theta )`$

$`dy = ( \bar r + w \bar x ) \sin ( d \theta )`$

After substituting in $`\bar r`$ and approximating the differentials,

$`\Delta x = \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) versin ( \Delta \theta )`$

$`\Delta y = \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) \sin ( \Delta \theta )`$

However, this assumes that the robot is always at $`(0, 0)`$ facing the positive y-axis.
To account for this, we do a polar basis transformation, to get:

$`x = \sum \Big [ \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) versin ( \Delta \theta ) \sin ( \theta ) + \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) \sin ( \Delta \theta ) \cos ( \theta ) \Big ] + C_x`$

$`y = \sum \Big [ \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) versin ( \Delta \theta ) \cos ( \theta ) + \Big ( { { \Delta s_1 + \Delta s_2 } \over 2 } + w \bar x \Big ) \sin ( \Delta \theta ) \sin ( \theta ) \Big ] + C_y`$

## Implementation Considerations

When $`\Delta \theta = 0`$, the function as written divides by zero.
However, this only occurs when driving straight, so $`dx = 0`$, $`dy = d \bar s`$.

$`dx = \lim \limits_ { \Delta \theta \to 0 } \Big ( { { { { \Delta s_1 + \Delta s_2} \over { \Delta \theta } } - w } \over 2 } + w \bar x \Big ) versin ( \Delta \theta )`$

$`= { 1 \over 2 } \lim \limits_ { \Delta \theta \to 0 } { { ( \Delta s_1 + \Delta s_2 - \Delta \theta w + 2 w \bar x ) versin ( \Delta \theta ) } \over { \Delta \theta } }`$

$`= { 1 \over 2 } ( \Delta s_1 + \Delta s_2 - \Delta \theta w + 2 w \bar x ) \lim \limits_ { \Delta \theta \to 0 } { { versin ( \Delta \theta ) } \over ( \Delta \theta ) }`$

$`= { 1 \over 2 } ( \Delta s_1 + \Delta s_2 - \Delta \theta w + 2 w \bar x ) \lim \limits_ { \Delta \theta \to 0 } { { \sin ( \Delta \theta ) } \over 1 }`$

$`= 0`$

$`dy = \lim \limits_ { \Delta \theta \to 0 } \Big ( { { { { \Delta s_1 + \Delta s_2} \over { \Delta \theta } } - w } \over 2 } + w \bar x \Big ) sin ( \Delta \theta )`$

$`= { 1 \over 2 } \lim \limits_ { \Delta \theta \to 0 } { { ( \Delta s_1 + \Delta s_2 - \Delta \theta w + 2 w \bar x ) \sin ( \Delta \theta ) } \over { \Delta \theta } }`$

$`= { 1 \over 2 } ( \Delta s_1 + \Delta s_2 - \Delta \theta w + 2 w \bar x ) \lim \limits_ { \Delta \theta \to 0 } { { \sin ( \Delta \theta ) } \over ( \Delta \theta ) }`$

$`= { 1 \over 2 } ( \Delta s_1 + \Delta s_2 - \Delta \theta w + 2 w \bar x ) \lim \limits_ { \Delta \theta \to 0 } { { \cos ( \Delta \theta ) } \over 1 }`$

$`= { 1 \over 2 } ( \Delta s_1 + \Delta s_2 - \Delta \theta w + 2 w \bar x )`$

$`= d \bar s`$
