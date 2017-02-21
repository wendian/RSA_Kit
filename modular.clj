; Basic GCD functions
(defn gcd "Basic Euclidean GCD" [a b]
	(if (= 0 b)
		a
		(gcd b (mod a b))))

(defn ex-gcd "Extended gcd that returns linear combination of the gcd" [a b]
	(if (= 0 b)
		(list a 1 0)
		(let [[d x y] (ex-gcd b (mod a b))]
			(list d y (- x (* (quot a b) y))))))

; Modular inverse finder
(defn mod-inv "Basic modular inverse of integer n under mod m" [n m]
	(if (= 0 m)
		nil
		(let [[d x y] (ex-gcd m n)]
			(if (= 1 d)
				(mod y m)
				nil))))

; Modular exponentiation functions
(defn mx [order curr exp n]
	(if (= 0 exp)
		(mod curr n)
		(mx (mod (* order order) n) (if (odd? exp) (mod (* order curr) n) curr) (quot exp 2) n)))

(defn mod-exp "Fast modular exponentiation" [base exp n]
		(mx base 1 exp n))