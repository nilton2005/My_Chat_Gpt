// Obtenemos el contrato
import {CreateMLCEngine}  from  "https://esm.run/@mlc-ai/web-llm"


// variables con $ son simbolos del DOM
const $ = el => document.querySelector(el)
const $form = $('form')
const $input = $('input')
const $template = $('#message-template')
const $messages = $('ul')
const $container = $('main')
const $button = $('button')
const $info = $('small')

let messages = []

const SELECTED_MODEL = "gemma-2b-it-q4f32_1-MLC"
const motor = await CreateMLCEngine(
    SELECTED_MODEL, 
    {
        initProgressCallback: (info)=>{
            console.log('esto sera la info de carga', info)
            $info.textContent =  `${info.text}`
            if(info.progress ==1){
                $button.removeAttribute('disabled')
            }
        }
})

$form.addEventListener('submit', async (event)=>{
    event.preventDefault()
    const messageText = $input.value.trim()
    if(messageText != ''){
        $input.value = ''
    }

    addMessage(messageText,'user' )
    $button.setAttribute('disabled','')

    const userMessage ={
        role:'user',
        content: messageText
    }
    messages.push(userMessage)
    // esto manda la respuesta al user
    const reply = await motor.chat.completions.create({
        messages

    })
    $button.removeAttribute('disabled','')
    const botMessages = reply.choices[0].message
    console.log(botMessages)
    messages.push(botMessages)
    addMessage(botMessages.content, 'bot')
})

function addMessage(text, sender){
    const clonedTemplate = $template.content.cloneNode(true)

    const $newMessage = clonedTemplate.querySelector('.message')
    const $who = $newMessage.querySelector('span')
    const $text = $newMessage.querySelector('p')

    $text.textContent = text
    $who.textContent = sender == 'bot'?'GPT':'Tú'

    $newMessage.classList.add(sender)
    $messages.appendChild($newMessage)
    
    // deve actuliar el scroll
    //scrollheight mede el tamaño de todo 
    $container.scrollTop = $container.scrollHeight
}