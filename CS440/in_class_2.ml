(* In class Assignment- 2 *)

(*q1*)
let min_option (x:int option) (y:int option) : int option = 
	match (x,y) with 
	| (None,None) -> None
	| (x,None) -> x
	| (None,y) -> y
	| (x,y) -> 
		if (x > y)
			then y
			else x;;
			
(* q2 *)
let get_option (x:int option option option option) : int option =
	match x with 
	| None -> None
	| Some None -> None
	| Some (Some None) -> None
	| Some (Some (Some x)) -> x
	
(* Q3 *)
(*  
Since if statment in Ocaml can only return the same type, so the function dosn't below type check.

The if statment's then is expected to return an int but the else are expected to return a list 

My small change:

let rec sumTo(n:int) : ?? =
	if n<=0 then ??
	else n + sumTo(n-1);;

*)