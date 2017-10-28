from flask import Flask


from flask import jsonify, request
from geopy.distance import vincenty
from geopy.geocoders import Nominatim
import time

from flask import make_response


app = Flask(__name__)





client_data = [{'id':1,
				'name':"shrey",
				'password':"123"
}]

help_stations=[
{
	'id':1,
	'type':'Fire_Station',
	'username':'jay',
	'password':'xyz',
	'latitude':28.5678,
	'longitude':77.3258,
	'time':time.time()*1000
}
]

sos_data = [

{
	'id':1,
	'type':'Fire',
	'username':'username',
	'password':'password',
	'latitude':28.5864,
	'longitude':77.3414,
	'time':time.time()
}

]



@app.route('/')
def hello():
	return 'hello duniyaaa'




@app.route('/help_station_add',methods=['POST'])
def help_station_add():
	iid = 1
	type1 = request.json['type']
	username1 = request.json['username']
	password1 = request.json['password']
	latitude1 = request.json['latitude']
	longitude1 = request.json['longitude']
	timee = time.time()

	word = {
		'id':1,
		'type':type1,
		'username':username1,
		'password':password1,
		'latitude':latitude1,
		'longitude':longitude1,
		'time':timee
		}
	

	#help_stations.append(aaa)

	f = open("help_stations_data.txt",'a')
	abc = ''

	#for word in sos_data:
	abc = str(word['type']) + ' ' + str(word['username']) + ' ' + str(word['password']) + ' ' + str(word['latitude']) + ' ' + str(word['longitude']) + ' ' + str(word['time']) + '\n'

	
	f.write(abc)
	f.close()

	return jsonify("help station added")





@app.route('/client_add',methods=['POST'])
def client_add():
	iid = 1
	username = request.json['username']
	password = request.json['password']
	word = {
		'id':1,
		'username':username,
		'password':password
		}
	
	client_data.append(aaa)
	f = open("clients_data.txt",'a')
	abc = ''

	#for word in sos_data:
	abc = str(word['username']) + ' ' + str(word['password']) + '\n'

	
	f.write(abc)
	f.close()
	return jsonify("client added")




@app.route('/sos_add',methods=['POST'])
def sos_add():
	iid = 1
	
	type1 = request.json['type']
	username1 = request.json['username']
	password1 = request.json['password']
	latitude1 = request.json['latitude']
	longitude1 = request.json['longitude']
	timee = request.json['time']
	
	word = {
		'id':1,
		'type':type1,
		'username':username1,
		'password':password1,
		'latitude':latitude1,
		'longitude':longitude1,
		'time':timee
		}
	
	#sos_data.append(aaa)

	f = open("sos_data.txt",'a')
	abc = ''

	#for word in sos_data:
	abc = str(word['type']) + ' ' + str(word['username']) + ' ' + str(word['password']) + ' ' + str(word['latitude']) + ' ' + str(word['longitude']) + ' ' + str(word['time']) + '\n'

	
	f.write(abc)
	f.close()

	return jsonify("SOS Added")





@app.route('/get_all_sos', methods=['GET'])
def get_all_sos():
	
	f = open('sos_data.txt','r')

	lines = f.readlines()

	abc = []

	for line in lines:
		sos = {
		
		'type':line.split()[0],
		'username':line.split()[1],
		'password':line.split()[2],
		'latitude':float(line.split()[3]),
		'longitude':float(line.split()[4]),
		'time':float(line.split()[5])
		}
		abc.append(sos)

	return jsonify({'sos data':abc})



@app.route('/get_all_clients', methods=['GET'])
def get_all_clients():
	f = open('clients_data.txt','r')

	lines = f.readlines()

	abc = []

	for line in lines:
		sos = {
		
		
		'username':line.split()[0],
		'password':line.split()[1],
		
		}
		abc.append(sos)
	return jsonify({'client data':abc})


@app.route('/get_all_help_stations', methods=['GET'])
def get_all_help_stations():
	f = open('help_stations_data.txt','r')

	lines = f.readlines()

	abc = []

	for line in lines:
		sos = {
		
		'type':line.split()[0],
		'username':line.split()[1],
		'password':line.split()[2],
		'latitude':(line.split()[3]),
		'longitude':(line.split()[4]),
		'time':(line.split()[5])
		
		}
		abc.append(sos)
	return jsonify({'help_stations data':abc})


@app.route('/clear_all', methods=['DELETE'])
def delete_all():
	f = open('clients_data.txt','w')
	f.close()
	f = open('sos_data.txt','w')
	f.close()
	f = open('help_stations_data.txt','w')
	f.close()
	return jsonify('deleted')


@app.route('/get_sos',methods=['POST'])
def get_sos():

	username = request.json.get('username',None)
	type1 = request.json.get('type',None)
	#old_time = request.json.get('time',0)
	old_time = 0.0
	help_location = (help_stations[0]['latitude'],help_stations[0]['longitude'])

	#return jsonify(help_stations)

	f = open('help_stations_data.txt','r')

	lines = f.readlines()

	abc = []

	for line in lines:
		help_data = {
		
		'type':line.split()[0],
		'username':line.split()[1],
		'password':line.split()[2],
		'latitude':(line.split()[3]),
		'longitude':(line.split()[4]),
		'time':(line.split()[5])
		
		}
		abc.append(help_data)

	f.close()
	f=open('help_stations_data.txt','w')
	for index, help_station in enumerate(abc):
		if help_station['username'] == username:
			old_time = help_station['time']
			help_station['time'] = time.time()

			llist = []
			for line in lines:
				if line.split()[5]==old_time:
					llist = line.split()
					llist[5] = str(help_station['time'])
					line = " ".join(llist)

				f.write(line+'\n')

			help_location = (help_station['latitude'],help_station['longitude'])
		else:
			for line in lines:
				f.write(line+'\n')

	#algo
	#return jsonify(help_location)
	f.close()
	new_sos = []
	
	deff = ""

	
	
	
	
	f = open('sos_data.txt','r')

	lines = f.readlines()

	abc = []

	for line in lines:
		sos = {
		
		'type':line.split()[0],
		'username':line.split()[1],
		'password':line.split()[2],
		'latitude':(line.split()[3]),
		'longitude':(line.split()[4]),
		'time':(line.split()[5])
		
		}
		if float(sos['time']) >= float(old_time) and type1 == sos['type']:
			sos_location = (sos['latitude'],sos['longitude'])
			if vincenty(sos_location,help_location).miles <= min_dist:
				loc = Nominatim(timeout=3)
				new_sos.append(loc.reverse(sos_location).address)


	f.close()

	
	
		
	return jsonify({'sos':new_sos})

max_time = 0.0
min_dist = 1
iii = 1
iii2 = 1
iii3 = 1

if __name__ == '__main__':
	app.run(debug=True)

