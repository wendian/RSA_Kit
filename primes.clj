(load-file "modular.clj")

; Primality tests using the modular number functions
(defn fermat? "One Fermat test n for prime" [n]
	(let [x (inc (rand-bigint (dec n)))]
		(= 1 (mod-exp x (dec n) n))))

(defn fast-fermat-prime? "Do k Fermat tests, k must be > 40 for the test to be reliable" [n k]
	(if (= 0 k)
		true
		(if (fermat? n)
			(fast-fermat-prime? n (dec k))
			false)))

(defn strong-prime? "true if n and (n-1)/2 are prime" [n]
	(and
		(fast-fermat-prime? n 50) 
		(fast-fermat-prime? (quot n 2) 50)))

; Actually finding a random prime
(defn rand-bigint [n] (bigint (rand n)))

(defn rand-bigint-h "Finds a strong prime between a and b, a < b" [a b]
	(+ a (bigint (rand (- b a)))))

(defn rand-odd-bigint []
	(let [n (rand-bigint-h 10000000000000N 99999999999999N)]
		(if (even? n)
			(inc n)
			n)))

(defn rep-till-sp [p]
	(if (strong-prime? p)
		p
		(rep-till-sp (+ 2 p))))

(defn big-strong-prime []
	(let [p (rand-odd-bigint)]
		(if (strong-prime? p)
			p
			(rep-till-sp (+ 2 p)))))

(defn close-strong-prime [p]
	(rep-till-sp (+ 2 p)))