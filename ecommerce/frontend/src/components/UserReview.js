import React, { useEffect, useState } from 'react'
import { Rate } from 'antd'
import { Link } from 'react-router-dom'
import './UserReview.less'
import { api } from '../api'
import { useAppContext } from '../context/AppContext'
import { notification } from 'antd'

export const UserReview = product_id => {
    const [isLoading, setIsLoading] = useState(false)
    const [reviews, setReviews] = useState([])

    // useEffect(() => {
    //     async function fetch() {
    //         try {
    //             setIsLoading(true)
    //             try {
    //                 const { data } = await api.get(`/product/${product_id}/reviews`)
    //                 setReviews(data.reviews)
    //                 // const { id, user_id, vendor_id, rating, comment, created_at } = data
    //             } catch (error) {
    //                 notification.error({ description: 'Failed to fetch comments' })
    //                 console.error(error)
    //             } finally {
    //             }
    //         } finally {
    //             setIsLoading(false)
    //         }
    //     }
    // }, [])

    const Review = ({ review: { comment, author, date, rating } }) => {
        return (
            <div className="review-item-content">
                <div className="rate-and-author">
                    <div className="rate">
                        <Rate defaultValue={rating} disabled={true}></Rate>
                    </div>
                    <Link to="#">
                        <div className="author">
                            <p>{author}</p>
                        </div>
                    </Link>
                </div>
                <div className="comment-and-date">
                    <p>{comment}</p>
                    <p>{date}</p>
                </div>
            </div>
        )
    }
    setReviews([
        {
            comment:
                'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
            author: 'Jeff Einstein',
            date: 'December 23',
            rating: 4,
        },
        {
            comment: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
            author: 'Hasan Kaya',
            date: 'December 11',
            rating: 3,
        },
    ])

    return (
        <div className="review-items">
            <h1>User Reviews</h1>
            {reviews.map((review, index) => {
                return (
                    <div key={review.id}>
                        <Review review={review} />
                        {index < reviews.length - 1 && <hr></hr>}
                    </div>
                )
            })}
        </div>
    )
}
