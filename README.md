# RSA_Kit
Implements RSA from scratch

##modular.clj
modular.clj contains all the functions that have to do with modular arithmetic.  Included are two Euclidean greatest common divisor functions, one simply finds the GCD of two numbers and the other finds the GCD and returns coeffiecients that turn the input numbers into a linear combination of the GCD.  The mod-inv function finds the modular inverse of a number n under mod, which is one of the main components in RSA encryption.  mod-exp is a "fast" modular exponentiation function that normally works in O(b^3) time where b is the number of bits in the exponent component, however, I am using Clojure's Bigint to represent my numbers, so that may not be true.

##primes.clj
primes.clj is a bunch of functions that help find and test prime numbers.  The Fermat test is incorporated in the fast-fermat-prime? function which in turn is used in strong-prime?; all to obtain the strong prime numbers for RSA to work.  The rest of the functions are simply there to find a random prime number.  To change the range of prime numbers, simply edit the range of nummbers found in rand-odd-bigint, the numbers I used here are tiny in comparison to actual RSA numbers for the sake of speed when testing.

##RSA.clj
Performs the the RSA parts of RSA.  It also has functions that convert text into numbers fit for RSA.  First, the string of text is converted to a list of bigints based on their ascii value - 32, in which case spacebars become zero.  Then, each letter per word is treated as the coefficient of a polynomial of x, where x = 91.  RSA then computes the modular exponentiation of the number based on the key.  To decrypt, RSA performs the same modular exponentiation with the private key and interpolates the numbers to obtain the original message in a list of ascii values which can easily be converted to readable text.

There is an example of this in the file example.clj
