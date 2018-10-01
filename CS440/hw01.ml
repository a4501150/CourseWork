(* Question 1 *)
let volume (height:float) (radius:float) : float =
	let squre x = x *. x in
	height *. squre radius *. 3.141
;;

(* Question 2 *)
let differences (n_1:float) (n_2:float) : float =
	abs_float (n_1 -. n_2)
;;

(* Question 3 *)
let isOddNumber (n:int) : bool =
	if n mod 2 <> 0 then true else false
;;

(* Question 4 *)
type student = {name:string ; id:int; score:float};;
(* put into new student *)
let new_student (n, i, s) : student =
	{name=n; id=i; score=s}
;;
(* extract from a student type *)
let new_student_tuple (s:student) =
	(s.name, s.id, s.score)
;;


(* Question 5 *)
let area (r:float) : float =
	let squre x = x *. x in
	squre r *. 3.141
;;