import { SignUpForm } from '../SignUpForm'
import { UpdateProfileForm } from '../UpdateProfileForm'
import './ProfileContent.less'

export const ProfileContent = ({ user }) => {
    
    return <UpdateProfileForm user={user} />
}
