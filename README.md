# mazerob #############################################################

**Maze solver robot**

(C) 2012 [Pedro Ivan Lopez](http://pedroivanlopez.com)

[*Project in development*]

Project **mazerob** is a robot that autonomously solves a maze.  The system
runs on Java Virtual Machines on a personal computer and a Lego NXT Brick
configured as a master-slave architecture.  Project mazerob was partially
funded by [UANL](http://www.uanl.mx) through the
[PROVERICYT](http://www.uanl.mx/universidad/investigacion/apoyos/provericyt.html)

- [Project website](http://pedroivanlopez.com/mazerob)
- [Online documentation](http://pedroivanlopez.com/mazerob/doc)

## Project dirs and files ############################################

- `README.txt`: This document.

- `LICENSE.txt`: Project's license and copyright information.

- `src`: Project's source code.

- `Makefile`: Makefile for configuring, building, flashing and running the
  software.  Also for building the documentation.

## Development #######################################################

mazerob has only been developed on Fedora 17 but development and deployment can
be ported to other Linux distributions and Windows too.  Follow these steps to
set yourself a development environment for project mazerob on a Linux
distribution:

1. Get a Lego NXT Brick, parts, sensors and actuators kit.

2. Flash the leJOS NXJ 0.9.1 firmware to your NXT (see [R1]_ section
   *Getting Started - Linux*).

3. Build a *Explorer* following the instructions at [R0]_.

4. Install leJOS NXJ 0.9.1 on a Bluetooth-enabled computer ([R1]_ section
   *Getting Started - Linux*).  Make sure `java`, `javac`, `javadoc`, `nxjc`,
   `nxj`, `nxjpcc` and `nxjpc` are in your `PATH` environment variable.  

5. Install utilities and libraries (names of Fedora 17 packages, look
   for your distribution's equivalents): bluecove, bluez-libs-devel,
   nxtrc, libusb-devel, gcc and libnxt.

6. Set a name for your NXT, for example using the nxtrc utility.  Set the
   `R0_NAME` variable in `Makefile` to that name.

7. Get your NXT Bluetooth address (using the nxtrc utility for example) and set
   the `R0_BLUETOOTH_ADDRESS` variable in `Makefile` to that address.

8. Read the `Makefile` configuration section at the beginning.  Tune the
   variables if needed.
   
9. Read the output of `make help`.

10. Run `make docs` to build the documentation and read it.

11. Read the `solveMaze` method example in the `mazerob.pc.MazeSolver` class.

> *Note*: To develop the actual program that will solve the maze, you don't
> have to modify the program that runs on the NXT brick, just modify the
> `solveMaze` method of the `mazerob.pc.MazeSolver` class.

## References ########################################################

- [R0] http://www.nxtprograms.com/NXT2/explorer/steps.html

- [R1] http://lejos.sourceforge.net/nxt/nxj/tutorial/index.htm
