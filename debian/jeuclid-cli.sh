#! /bin/sh

# Shell script wrapper around the jeuclid-cli program,
# Copyright 2008 by Sylvestre Ledru <sylvestre.ledru@inria.fr>
#
# Licensed under the same terms as jeuclid-cli itself, that is under
# the conditions of the Apache 2 licencee.

# Include the wrappers utility script
. /usr/lib/java-wrappers/java-wrappers.sh


# We prefer to use openjdk or Sun's java if available
find_java_runtime openjdk sun  || find_java_runtime 

find_jars jeuclid-cli commons-cli commons-logging batik-all xmlgraphics-commons
find_jars commons-io commons-lang jeuclid-core

run_java net.sourceforge.jeuclid.app.Mml2xxx "$@"
