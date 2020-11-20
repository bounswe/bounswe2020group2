import './ProfileDetails.less'
import { Link } from 'react-router-dom'

export const ProfileDetails = () => {
    return (
        <div className="profile-details">
            <ul>
                <li>
                    {' '}
                    <Link to="/profile/change-password">Change Password</Link>{' '}
                </li>
                <li>
                    {' '}
                    <Link to="/profile/purchases">Purchase History</Link>{' '}
                </li>
                <li>
                    {' '}
                    <Link to="/profile/messages">Messages</Link>{' '}
                </li>
            </ul>
        </div>
    )
}
