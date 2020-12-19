from rest_framework import exceptions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from ..utils import can_review_product, can_review_vendor
from ..utils import permissions
from ..models import Review, Product, Vendor
from ..utils import validate_review_request
from ..serializers import review_serializer

@api_view(['GET', 'POST', 'DELETE'])
@permission_classes([permissions.AllowAnonymous])
def manage_review(request):
    
    #GET functionalities
    if request.method == 'GET':
        review_id = request.query_params.get("review", None)
        product_id = request.query_params.get("product", None)
        vendor_id = request.query_params.get("vendor", None)

        #Get a single review with id
        reviews = []
        if review_id is not None:
            review = Review.objects.filter(pk=review_id).filter(is_deleted=False).first()
            if review is not None:
                reviews.append(review)
            else:
                return Response(status=status.HTTP_404_NOT_FOUND)
        #Get all reviews of a product
        elif product_id is not None:
            product = Product.objects.filter(pk=product_id).first()
            if product is not None:
                reviews = Review.objects.filter(product=product).filter(is_deleted=False).all()
            else:
                return Response(status=status.HTTP_404_NOT_FOUND)
        
        #Get all reviews of a vendor
        elif vendor_id is not None:
            vendor = Vendor.objects.filter(pk=vendor_id).first()
            if vendor is not None:
                reviews = Review.objects.filter(vendor=vendor).filter(is_deleted=False).all()
            else:
                return Response(status=status.HTTP_404_NOT_FOUND)

        review_serialized = review_serializer.ReviewResponseListSerializer(
                    reviews , 
                    context = { 'is_successful': True,
                                'message': ""}
                )
        return Response(review_serialized.data)


    #POST functionality
    if request.method == 'POST':
        uid = request.user.pk
        if uid != request.data["user_id"]:
            return Response(status=status.HTTP_403_FORBIDDEN)
        validation_result = validate_review_request(request)
        if not validation_result[0]:
            return Response(status=status.HTTP_400_BAD_REQUEST, data=validation_result[1])

        if request.data["product_id"] is not None:
            pid = request.data["product_id"]
            able_to_review = can_review_product(uid, pid)
            
            if able_to_review[0]:
                product = Product.objects.filter(pk=pid).first()
                product.rating_count += 1
                product.total_rating += request.data["rating"] 
                product.save()
                review = Review(reviewed_by=request.user, product=product, rating=request.data["rating"], comment=request.data["comment"])
                review.save()
                review_serialized = review_serializer.ReviewResponseSerializer(
                    review, 
                    context = { 'is_successful': True,
                                'message': "Review successfully saved."}
                )
                return Response(review_serialized.data)
            
            review_serialized = review_serializer.ReviewResponseSerializer(
                    Review(), 
                    context = { 'is_successful': False,
                                'message': able_to_review[1]}
                )
            return Response(review_serialized.data)
        else:
            vid = request.data["vendor_id"]
            able_to_review = can_review_vendor(uid, vid)
            if able_to_review[0]:
                vendor = Vendor.objects.filter(pk=vid).first()
                vendor.total_rating += request.data["rating"]
                vendor.rating_count +=1
                vendor.save()
                review = Review(reviewed_by=request.user, rating=request.data["rating"], comment=request.data["comment"], vendor=vendor)
                review.save()
                review_serialized = review_serializer.ReviewResponseSerializer(
                    review, 
                    context = { 'is_successful': True,
                                'message': "Review successfully saved."}
                )
                return Response(review_serialized.data)
            review_serialized = review_serializer.ReviewResponseSerializer(
                    Review(), 
                    context = { 'is_successful': False,
                                'message': able_to_review[1]}
                )
            return Response(review_serialized.data)
    
    #DELETE functionality
    if request.method == 'DELETE':
        review_id = request.data["id"]
        if review_id is None:
            return Response(status=status.HTTP_400_BAD_REQUEST)
        review = Review.objects.filter(pk = review_id).filter(is_deleted=False).first()
        if review is None:
            review_serialized = review_serializer.ReviewResponseSerializer(
                    Review(), 
                    context = { 'is_successful': False,
                                'message': 'Review is not found.'}
                )
            return Response(review_serialized.data)
        if review.reviewed_by != request.user:
            return Response(status=status.HTTP_403_FORBIDDEN)
        
        review.is_deleted = True
        if review.product is not None:
            review.product.total_rating -= review.rating
            review.product.rating_count -= 1
            review.product.save()
        else:
            review.vendor.total_rating -= review.rating
            review.vendor.rating_count -= 1
            review.vendor.save()
        review.save()

        review_serialized = review_serializer.ReviewResponseSerializer(
                    Review(), 
                    context = { 'is_successful': True,
                                'message': 'Review is deleted successfully.'}
                )
        return Response(review_serialized.data)
