# Simple curl to check if a http response is correct

address=$1
port=$2
code=$3
path=$4
echo "Check health for address $address on port $port, if code is $code"

response=$(curl -o /dev/null -s -w "%{http_code}\n" http://$address:$port$path)

if [ "$code" = "$response" ]
then 
	echo "-> Check is ok..."
else
	echo "-> Check is not ok..."
fi

