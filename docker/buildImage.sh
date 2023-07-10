cp ../core/target/utb-backend.jar .
IMAGE_NAME=czechprz/utb-backend
sudo docker image rm -f $IMAGE_NAME
sudo docker image build --tag $IMAGE_NAME .
sudo docker image push $IMAGE_NAME
