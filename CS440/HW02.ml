(* Q1 *)
let rec fact x = function
	if x <= 1 then 1 else x * fact (x - 1);;
(* Q2 *)
let rec sort = function
    | [] -> []
    | h::t -> match select h t with
      | h, t -> h::sort t
	  and select x = function
	| [] -> x, []
    | h::t ->
        let x, h = if x<h then x, h else h, x in
        let x, t = select x t in
        x, h::t;;
(* Q3 *)
(*
the first ?? is an argument type of x function. it is 'a (anything).
the second ?? is a return value type of function forever, which is forever. *) 

(* Q4 *)
let rec add_list = function 
	| [] -> []
	| (h:int) :: t -> 
		let add x = x + 3 in
		add h :: add_list t;;

(* Q5 *)
let rec fib n = 
	if n<2 then n 
	else fib(n-1) + fib(n-2);;
	
(* Q6 *)
let rec count_odd_in_list = function 
	| [] -> 0
	| (h:int) :: t -> 
		if h mod 2 <> 0 then 
		1 + count_odd_in_list t
		else count_odd_in_list t;;
		
(* Q7 *)
let rec sum_list = function 
	| [] -> None
	| (h:int) :: t -> 
	Some (sum_rec t + h)
	and sum_rec = function
		| [] -> 0
		| (h:int) :: t -> 	
			 h + (sum_rec t);;
	
		
(* Q8 *)
let rec reverse_tuple_in_list = function
	| [] -> []
	| (a,b) :: t ->
	 reverse_tuple (a,b) :: reverse_tuple_in_list t
	 and reverse_tuple (pair : int*string) = 
			let (r1,r2) = pair in
				(r2,r1);;

