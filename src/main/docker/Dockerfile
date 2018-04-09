FROM java:8
ADD capitalonedemo-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
RUN bash -c 'touch /app.jar'
ADD run_demo.sh .
RUN chmod +x run_demo.sh
CMD bash run_demo.sh
