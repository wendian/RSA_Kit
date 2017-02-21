(load-file "RSA.clj")
; Get big primes, if bigger primes are needed, change the values in the range of the rand-odd-bigint function in primes.clj
(def p (big-strong-prime))
(def q (close-strong-prime p))

; Calculate the n modulus value
(def n (* p q))
(def m (* (dec p) (dec q)))

; Create public and private keys
(def e (find-e m))
(def d (mod-inv e m))

; Defining the set of keys
(def public-key (make-key n e))
(def private-key (make-key n d))

; Encrypting plain text
(def test1 (encrypt-msg "1 12 123 1234 12345 123456 1234567 12345678 123456789 1234567890 12345678901 123456789012 1234567890123 12345678901234" public-key))
(def test2 (encrypt-msg "a a a a a a" public-key))
(def test3 (encrypt-msg "a-a-a-a-a-a" public-key))
(def message "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
(def mess (encrypt-msg message public-key))

; Decrypting Cyphertext
(decrypt-msg test1 private-key)
(decrypt-msg test2 private-key)
(decrypt-msg test3 private-key)
(decrypt-msg mess private-key)