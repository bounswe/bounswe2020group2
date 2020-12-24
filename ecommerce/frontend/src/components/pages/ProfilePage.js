import './ProfilePage.less'
import { ProfileDetails } from '../profile/ProfileDetails'
import { ProfileContent } from '../profile/ProfileContent'
import getflixLogo from '../../assets/logo.png'
import { useAppContext } from '../../context/AppContext'

export const ProfilePage = () => {
    // future version
    const { user } = useAppContext()
    return (
        <div>
            <div className="logo-name-splash">
                <img src={getflixLogo} className="splash-logo"></img>
                <div className="splash-slogan">
                    <h1>
                        {user.name}&nbsp;&nbsp;{user.lastname}
                    </h1>
                </div>
            </div>
            <div className="profile-page-wrapper">
                <div className="left-bar-profile-details">
                    <ProfileDetails />
                </div>
                <div className="right-bar-profile-content">
                    <ProfileContent key={user.id} user={user} />
                </div>
            </div>
        </div>
    )
}
