if [ -z $1 ] ; then
  echo "specify the day"
  exit
fi

lein new day-${1}

mkdir -p day-${1}/data/
# if a second arg, paste the clipboard into the data dir
if [ -n "$2" ] ; then
  pbpaste > day-${1}/data/data.txt
fi

