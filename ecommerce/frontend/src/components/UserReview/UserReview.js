import moment from 'moment'
import { Rate } from 'antd'
import { Link } from 'react-router-dom'
import './UserReview.less'
export const UserReview = ({ review: { comment, user_id, created_at, rating } }) => {
    const dateFmt = moment(created_at).format('LL')

    return (
        <div className="review-item-content">
            <div className="review-rate-and-message">
                <div className="review-rate">
                    <Rate defaultValue={rating} disabled></Rate>
                </div>
                <div className="review-message">{comment}</div>
            </div>
            <div className="review-author-and-date">
                <Link to="/">
                    <div className="review-author">{user_id}</div>
                </Link>
                <div className="review-date">{dateFmt}</div>
            </div>
        </div>
    )
}
