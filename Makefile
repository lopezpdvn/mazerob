# mazerob Makefile
# (C) 2012 Pedro I. López <dreilopz@gmail.com>

# ================ Begin configuration ================
# The name of the NXT
R0_NAME='r0'

# The bluetooth address of the NXT
R0_BLUETOOTH_ADDRESS='00:16:53:06:68:A3'

# Diameter of the tires in mm
R0_WHEEL_DIAMETER='43.2'

# Distance between center of right tire and center of left tire in mm
R0_TRACK_WIDTH='139.0'

# If true, the NXT robot moves forward when the motors are running backward.
# true/false values only.
R0_REVERSE='false'

# Rotation speed of the vehicle, in degrees per second
R0_ROTATION_SPEED='90.0'

# Magnitude of translation in mm of methods ``translateForward`` and
# ``translateBackward`` in class ``mazerob.nxt.Robot``
R0_TRANSLATION_MAGNITUDE='100.0'

# Magnitude of rotation in degrees of methods ``rotateRight`` and
# ``rotateLeft`` in class ``mazerob.nxt.Robot``
R0_ROTATION_MAGNITUDE='85.0'
# ================ End configuration ==================

DOCS_MSG="mazerob's documentation [IN DEVELOPMENT]"
DOCS_LICENSE_MSG='Licensed under the New BSD License'
DATE=`date`

help:
	@echo "Use \`\`make <target>\`\` where <target> is one of:"
	@echo "  docs    to build the project's documentation"
	@echo "  nxt     to compile the NXT brick program"
	@echo "  upload  to upload the NXT brick program"
	@echo "  pc      to compile the PC program"
	@echo "  pcrun   to run the PC program"
	@echo "  clean   to clean the environment"
	@echo "  push    push selected branches to remote repository"

docs:
	javadoc -charset 'UTF-8' \
	-d ./doc \
	-bottom $(DOCS_LICENSE_MSG)"<br>$(DATE)" \
	-doctitle $(DOCS_MSG) \
	-author \
	-footer "" \
	-header "Project mazerob" \
	-private \
	-verbose \
	-windowtitle $(DOCS_MSG) \
	-keywords \
	-linksource \
	-sourcepath src \
	-overview src/mazerob/package.html \
	-link http://lejos.sourceforge.net/nxt/pc/api/ \
	-link http://lejos.sourceforge.net/nxt/nxj/api/ \
	-use \
	 mazerob.conn mazerob.nxt mazerob.pc

mkbuilddir:
	mkdir -p build

nxt: mkbuilddir
	nxjc -d build -cp src src/mazerob/nxt/RobotApp.java

upload: nxt
	nxj -d $(R0_BLUETOOTH_ADDRESS) -b -cp build mazerob.nxt.RobotApp

pc: mkbuilddir
	nxjpcc -d build -cp src src/mazerob/pc/PC.java

pcrun:	pc
	nxjpc -cp build mazerob.pc.PC \
	$(R0_NAME) \
	$(R0_BLUETOOTH_ADDRESS) \
	$(R0_WHEEL_DIAMETER) \
	$(R0_TRACK_WIDTH) \
	$(R0_REVERSE) \
	$(R0_ROTATION_SPEED) \
	$(R0_TRANSLATION_MAGNITUDE) \
	$(R0_ROTATION_MAGNITUDE)

clean:
	rm -rf doc/* build/*

push:
	git push origin master dev
