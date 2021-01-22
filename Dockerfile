FROM openjdk:8u151-jdk

WORKDIR /app
COPY . /app
#Install Scala
RUN tar xvf scala-2.13.4.tgz && \
	mv scala-2.13.4 /usr/lib && \
	ln -s /usr/lib/scala-2.13.4 /usr/lib/scala && \
	echo "export PATH=$PATH:/usr/lib/scala/bin" >> ~/.bashrc && \
	rm scala-2.13.4.tgz && \
    chmod a+x ./run.sh
ENTRYPOINT ["bash"]
