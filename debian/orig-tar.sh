#!/bin/sh -e

# called by uscan with '--upstream-version' <version> <file>
DIR=jeuclid-parent-$2
TAR=../jeuclid_$2.orig.tar.gz

# clean up the upstream tarball
unzip $3 
# Remove jeuclid-testsuite because it is not used and there are some license
# issues
# Remove the license file of FreeHEP because we disabled the used of this
# feature
tar -c -z -X debian/orig-tar.exclude -f $TAR $DIR/
rm -rf $DIR $3

# move to directory 'tarballs'
if [ -r .svn/deb-layout ]; then
    . .svn/deb-layout
    mv $TAR $origDir
    echo "moved $TAR to $origDir"
fi

