import React, { useEffect, useState } from 'react'
import { Rate, Pagination, Spin } from 'antd'
import { Link } from 'react-router-dom'
import './UserReview.less'
import { sleep } from '../utils'
import { api } from '../api'
import moment from 'moment'

export const UserReview = ({ productId = 1, pageSize = 10 }) => {
    const [isLoading, setIsLoading] = useState(true)
    const [currentPage, setCurrentPage] = useState(1)
    const [reviews, setReviews] = useState([])

    useEffect(() => {
        // When current page is changed, makes a call to backend and gets the new reviews.
        async function fetch() {
            try {
                setIsLoading(true)
                const tmp = [
                    {
                        comment:
                            'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
                        author: 'Jeff Einstein',
                        date: '2020-12-11T14:48:00.000Z',
                        rating: 4,
                    },
                    {
                        comment: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
                        author: 'Hasan Kaya',
                        date: '2011-10-05T14:48:00.000Z',
                        rating: 3,
                    },
                    {
                        comment:
                            'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
                        author: 'Jeff Einstein',
                        date: '2011-10-05T14:48:00.000Z',
                        rating: 4,
                    },
                    {
                        comment: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
                        author: 'Hasan Kaya',
                        date: '2011-10-05T14:48:00.000Z',
                        rating: 3,
                    },
                    {
                        comment:
                            'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
                        author: 'Jeff Einstein',
                        date: '2011-10-05T14:48:00.000Z',
                        rating: 4,
                    },
                    {
                        comment: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.',
                        author: 'Hasan Kaya',
                        date: '2011-10-05T14:48:00.000Z',
                        rating: 3,
                    },
                ]
                const urlStr = `/product/${productId}/review?pagesize=${pageSize}&currentpage=${currentPage}`
                // console.log(urlStr)
                // const { data: reviews } = await api.get(urlStr)
                setReviews(tmp)
                await sleep(2000)
            } catch (error) {
                console.error('Failed to load user reviews', error)
            } finally {
                setIsLoading(false)
            }
        }
        fetch()
    }, [currentPage])

    const onPaginationChanged = value => {
        // When page count is changed, current page is set
        console.log('Page changed to ', value)
        setCurrentPage(value)
    }

    const Review = ({ review: { comment, author, date, rating } }) => {
        const dateFmt = moment(date).format('LL')

        return (
            <div className="review-item-content">
                <div className="rate-and-message">
                    <div className="rate">
                        <Rate defaultValue={rating} disabled></Rate>
                    </div>
                    <div className="message">
                        <p>{comment}</p>
                    </div>
                </div>
                <div className="author-and-date">
                    <Link to="/">
                        <div className="author">
                            <p>{author}</p>
                        </div>
                    </Link>
                    <div className="date">
                        <p>{dateFmt}</p>
                    </div>
                </div>
            </div>
        )
    }

    return (
        <div className="user-review">
            <Spin spinning={isLoading}>
                <h1>User Reviews</h1>
                <div className="user-review-items">
                    {reviews.map((review, index) => {
                        return (
                            <div key={review.id}>
                                <Review review={review} />
                                {index < reviews.length - 1 && <hr style={{ margin: 0 }}></hr>}
                            </div>
                        )
                    })}
                </div>
            </Spin>

            <Pagination
                className="review-results-pagination"
                onChange={onPaginationChanged}
                defaultCurrent={currentPage}
                total={60}
            />
        </div>
    )
}
