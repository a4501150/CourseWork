package cs430;

import java.util.*;

public class State {
	
    Vector cups;
    State previos;
    State nextState;
    

    State (Vector cups, State previous) {
       this.cups = cups;
       this.previos = previous;
    }



}
