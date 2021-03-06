import atexit
import socket
import select
import sys
import string

from utils import *

def send_to_server(client_socket, command):
    '''Send the given message to the given recipient'''
    header, request = serialize_request(command)

    if header and request:
        try:
            client_socket.send(header)
            client_socket.send(request)
        except socket.error:
            print 'Server disconnected'

def prompt():
    sys.stdout.write('>> ')
    sys.stdout.flush()

if __name__ == "__main__":

    if (len(sys.argv) < 4):
        print 'Incorrect syntax: python chat_client.py hostname port username'
        sys.exit()

    host = sys.argv[1]
    port = int(sys.argv[2])
    username = sys.argv[3]

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.settimeout(5)

    try:
        client_socket.connect((host, port))
    except:
        print 'Failed to connect'
        sys.exit()

    send_to_server(client_socket, 'create_account %s' % username)
    send_to_server(client_socket, 'login %s' % username)
    print 'Welcome ' + username + '.  Type "help" for help.'

    @atexit.register
    def logout():
        send_to_server(client_socket, 'logout')
    
    while True:

        sock_list = [sys.stdin, client_socket]

        read_sockets, _, _ = select.select(sock_list, [], [])
        
        for s in read_sockets:
            if s == client_socket:
                data = s.recv(HEADER_SIZE)
                if data:
                    server_version, payload_size = parse_header(data)
                    if server_version != VERSION:
                        raise Exception('Client version %s does not match server version %s' % (VERSION, server_version))
                    received_message = s.recv(payload_size)
                    print received_message
                    prompt()
                else:
                    print 'Server disconnected'
                    sys.exit()

            else: #  data from stdin
                message = sys.stdin.readline()
                if message.startswith('help'):
                    print get_help()
                else:
                    send_to_server(client_socket, message)
                prompt()

                

