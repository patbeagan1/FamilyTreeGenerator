# Family Tree Generator

This project allows for the creation of fictional family trees. 

### Why? 

Family trees have always seemed like a fun data structure, so I wanted to play around with them. They can take a while to build from scratch though. This allows for easy family tree generation and viewing. 

### Prerequisites

You'll need `graphviz` for this. If you are on mac you can just do `brew install graphviz`.

### What does the output look like? 

1. If you run the `main` function, you'll get a family.dot file generated in the top level of the repo. 
2. Running `generateImage.sh` will generate an image for the dotfile, and open it. 

The generated image should look something like this: 
![sample image](./art/sample.jpg)

---

### How can I change the names? 

If you'd like to run it with different names, the lists that are used to generate people names are right in the `Main.kt` file - they should be straightforward to edit.

### Why are the people numbered? 

The person ids ensure that they are unique within the graph. Otherwise, there is a small chance that two people will have the same name and create a cycle - not valid for a family tree!

### Why is there so much output?

Right now I have it set up to generate a couple of ancestors for people that marry into the family later on. This helps make it seem less like they are popping up out of nowhere. You can disable this by passing `Default` into the random person generator, instead of `WithFamily`.

### How are last names determined? 

Right now they are fixed patrilinearly - children keep the father's last name. There are [other ways that this could be done](https://en.wikipedia.org/wiki/Surname), and maybe they will be added in the future. 

### Roadmap

I put this together for fun, and don't plan on maintaining it regularly. If you see something that you would like me to change though, feel free to add an issue and I may get to it. 
