(load-file "primes.clj")

; Message conversion
(defn dec32 [x] (- x 32))

(defn string-ascii [s] (map dec32 (map int s)))

(defn b91 [v sum]
	(if (empty? v)
		sum
		(b91 (rest v) (* 91 (+ sum (first v))))))

(defn ascii-num91 "Converts string to one base 91 to include ascii up to lowercase z" [s] (/ (b91 s 0N) 91))

; Key generation, find a public key part public-exp
(defn finder [m e]
	(if (= 0 e)
		nil
		(if (= 1 (gcd e m))
			(if (= nil (mod-inv e m))
				(finder m (dec e))
				e)
			(finder m (dec e)))))

(defn find-e [m] (finder m (- m 2)))

; Key distribution
; constructor
(defn make-key [mod exp] (list mod exp))

; selectors
(defn get-mod [pkey] (first pkey))
(defn get-exp [pkey] (second pkey))


; Word encryption
(defn encrypt-num [number publickey] (mod-exp (bigint number) (get-exp publickey) (get-mod publickey)))

(defn encrypt-word [plaintext publickey]
	(encrypt-num (bigint (ascii-num91 (string-ascii plaintext))) publickey))

(defn stringer [nm s]
	(if (= 0 nm)
		s
		(stringer (/ (- nm (mod nm 91)) 91) (str (char (+ 32 (mod nm 91))) s))))

(defn num-string [nm]
	(if (= 0 nm)
		" "
		(stringer nm "")))

(defn decrypt-word [cyphertext privatekey]
	(num-string (mod-exp cyphertext (get-exp privatekey) (get-mod privatekey))))

; Encryption of a sentence
(defn applier [publickey]
	(fn [words] (encrypt-word words publickey)))

(defn encrypt-msg [msg publickey]
	(map (applier publickey) (partition-by (fn[x] (= x \space)) msg)))


; Decryption of a sentence
(defn applier2 [privatekey]
	(fn [words] (decrypt-word words privatekey)))

(defn decrypt-msg [msg privatekey]
	(apply str (map (applier2 privatekey) msg)))

