import React, { Component, Fragment, useEffect } from 'react'
// allow for links to other webpages
import { Link, Redirect } from 'react-router-dom'
import axios from 'axios'
import '../../styles/CustomerDashboard.css'

class Home extends Component {
  constructor() {
    super()

    this.state = {
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  componentDidMount() {
    axios.get('http://localhost:8090/customers')
      .then(response => {
        console.log(response)
        response.data.forEach(customer => {
          if(customer.loggedIn) {
            this.setState({
              customer: customer,
              oldpassword: '',
              newpassword: '',
              email: '',
            })
          }
        })
        console.log(this.state)
      })
      .catch(error => {
        console.log(error)
      })
  }

  // sets the state when inputs in sign up form are filled
  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value })
  }

  // handles POST request when form is submitted
  handleSubmit = (e) => {
    e.preventDefault()
    console.log(this.state)
    axios
      .post('http://localhost:8090/customer/request/', this.state)
      .then((response) => {
        console.log(response)
        const {temp} = this.state
        this.setState({
          ...temp,
          status: response.data
        })
        console.log(this.state)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  render() {
    const { oldpassword, newpassword, email } = this.state

    // redirect if password change request goes through
    if(this.state.status === 'Password change requested.')
    {
        return (
          <div>
              <div class='sectionTitle'>
                <p>Request Password Change</p>
              </div>

              <p class='requestSent'>Request sent!</p>
          </div>
        )
    }


    return (
        <div>
          <div class='sectionTitle'>
            <p>Request Password Change</p>
          </div>

          <div >
            <form 
              class='passChangeForm' 
              onSubmit={this.handleSubmit}
            >
              <label for='email'>E-mail:</label>
              <br></br>
              <input
                onChange={this.handleChange}
                type='email'
                id='email'
                name='email'
                // placeholder='your current password'
                value={email}
                size='70'
              ></input>
              <br></br>
              <br></br>

              <label for='oldpassword'>Current Password:</label>
              <br></br>
              <input
                onChange={this.handleChange}
                type='password'
                id='oldpassword'
                name='oldpassword'
                // placeholder='your current password'
                value={oldpassword}
                size='70'
              ></input>
              <br></br>
              <br></br>

              <label for='newpassword'>New Password:</label>
              <br></br>
              <input
                onChange={this.handleChange}
                type='password'
                id='newpassword'
                name='newpassword'
                // placeholder='your new password'
                value={newpassword}
                size='70'
              ></input>
              <br></br>
              <br></br>

              <br></br>

              <button class='submit' type='submit'>
                Submit
              </button>
            </form>
          </div>
      </div>
    )
  }
}

export default Home
