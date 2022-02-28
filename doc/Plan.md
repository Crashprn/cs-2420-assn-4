
Design:

makeHashTable
    takes text file and parses it
    cases for insertion
        if in
            update the wordinfo object
        if not in
            add to hash table
        if i -1 greater than zero
            update the previous wordinfo object

Implementation:

makeHashTable
    parse the file into lines
    call contains on the word
        if true update the word freq object
        else make new info object and insert it into the list
    
        