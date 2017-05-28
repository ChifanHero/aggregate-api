file_name=services/target/integration-tests/run/es.pid
if test -e "$file_name";then
    value=$(<$file_name)
    kill -9 $value
    rm $file_name
fi
mvn clean install
if test -e "$file_name";then
    value=$(<$file_name)
    kill -9 $value
    rm $file_name
fi