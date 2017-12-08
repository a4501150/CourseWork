"""
- due to BFS, the found path is guaranteed to be shortest.

To run, - $ python3 waterJug.py

python3 is needed for run.
"""

from collections import deque

jug_amount = 0 ;

def other(num1,num2):
    """helper function to find the third index given two of the three indices 0,1,2."""
    return (3-num1-num2)%3


def get_transitions(state, bound):
    """finds states that can be visited from current state while satisfying given bound."""
    states = set()
    curr = [None, None, None]
    for i in range(3):
        for j in range(3):
            if i != j and state[i] != 0:
                # Find states that result in pouring i into j
                curr[other(i,j)] = state[other(i,j)]
                amt = min([state[i], bound[j] - state[j]])
                curr[i] = state[i] - amt
                curr[j] = state[j] + amt
                states.add(tuple(curr))
    return states


def print_path(final_state, parent_dict):

    print('path to final state:')
    path = list()
    while final_state in parent_dict:
        path.append(final_state)
        final_state = parent_dict[final_state]
    path.reverse()
    for p in path:
        print(p)


def search(init_state, bound, final_num):
    """performs BFS from init_state and tries to reach any state with final_num in it."""
    parent = dict()
    bfs_queue = deque()
    visited = set()
    bfs_queue.append(init_state)
    parent[init_state] = None

    while bfs_queue:
        curr = bfs_queue.popleft()
        if final_num in curr:
            print_path(curr, parent)
            return
        visited.add(curr)
        new_states = get_transitions(curr, bound)
        for s in new_states:
            if s not in visited:
                parent[s] = curr
                bfs_queue.append(s)

    print('unable to reach any state with {}'.format(final_num))


def main():

    jugs = input("Input capacity of each jug , separated by space, then press enter.\n")
    target = input("Input target water amount, you want then press enter: ")

    jugs = [int(x) for x in jugs.split()]
    target = int(target)

    jugs.sort()
    init_state = 0,0,jugs[-1]

    bound = jugs

    global jug_amount
    jug_amount = len(jugs)

    print ("Amount of Jugs is %d" % jug_amount)

    print('search for {}'.format(target))
    search(init_state, bound, target)



if __name__ == '__main__':
    main()