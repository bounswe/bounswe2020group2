import React from 'react'
import { Button, Rate, Card } from 'antd'
import { Link } from 'react-router-dom'
import './UserReview.less'
import { useAppContext } from '../context/AppContext'

export const UserReview = () => {
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
    const reviews = [
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
    ]

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
